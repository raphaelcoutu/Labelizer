package com.raphaelcoutu.labelizer.service;

import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.LabelBox;
import com.raphaelcoutu.labelizer.entity.Photo;
import com.raphaelcoutu.labelizer.entity.ReportRequest;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ReportServiceImpl implements ReportService {
    private final Path tempRoot = Paths.get("temp");

    private final PhotoRepository photoRepository;

    public ReportServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void init() {
        // Création du dossier temp root
        if (Files.notExists(tempRoot)) {
            try {
                Files.createDirectory(tempRoot);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public Path createTempDirectory() throws IOException {
        return Files.createDirectory(tempRoot.resolve(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))));
    }

    @Override
    public void createClassesFile(Path tempDir, List<Label> labels) throws IOException {
        // Création du fichier classes.txt
        FileUtils.writeLines(tempDir.resolve("classes.txt").toFile(), labels.stream().map(Label::getName).collect(Collectors.toList()));
    }

    @Override
    public void createPhotosAndAnnotations(Path tempDir, ReportRequest request) throws IOException {
        List<Long> labelIds = request.getLabels().stream().map(Label::getId).collect(Collectors.toList());
        List<Photo> photos = photoRepository.findPhotosWithLabels(labelIds);

        for (Photo photo : photos) {
            // Copier image dans répertoire temporaire
            File source = Paths.get("uploads").resolve(photo.getFilename() + "." + photo.getExtension()).toFile();
            File destination = tempDir.resolve(photo.getFilename() + "." + photo.getExtension()).toFile();
            FileUtils.copyFile(source, destination);

            // Créer fichier d'annotation
            FileWriter photoWriter = new FileWriter(tempDir.resolve(photo.getFilename() + ".txt").toFile());
            for (LabelBox labelBox : photo.getLabelBoxes()) {
                int objectClass = labelIds.indexOf(labelBox.getLabel().getId());
                // x et y doivent être le centre du carré
                float x = (float) (labelBox.getX() + labelBox.getWidth() / 2) / photo.getWidth();
                float y = (float) (labelBox.getY() + labelBox.getHeight() / 2) / photo.getHeight();
                float width = (float) labelBox.getWidth() / photo.getWidth();
                float height = (float) labelBox.getHeight() / photo.getHeight();
                String labelBoxString = String.format(Locale.US, "%d %f %f %f %f\n", objectClass, x, y, width, height);
                photoWriter.write(labelBoxString);
            }
            photoWriter.close();
        }
    }

    @Override
    public void createDownload(Path tempDir, HttpServletResponse response) {
        try {
            String zipFile = tempDir + ".zip";
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            File folderToZip = tempDir.toFile();
            response.setContentType("application/text");
            response.setHeader("Content-disposition", "attachment; filename=" + tempDir.getFileName() + ".zip");

            zipFile(folderToZip, folderToZip.getName(), zipOut);

            OutputStream out = response.getOutputStream();
            FileInputStream zin = new FileInputStream(zipFile);

            zipOut.close();

            FileCopyUtils.copy(zin, out);

            zin.close();
            fos.close();
            out.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void deleteTempFiles(Path tempDir) throws IOException {
        this.removeDirectory(tempDir.toFile());
        Files.deleteIfExists(Paths.get(tempDir + ".zip"));
    }

    private void zipFile(File fileToZip, String filename, ZipOutputStream zipOutputStream) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (filename.endsWith("/")) {
                zipOutputStream.putNextEntry(new ZipEntry(filename));
            } else {
                zipOutputStream.putNextEntry(new ZipEntry(filename + "/"));
            }
            zipOutputStream.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, filename + "/" + childFile.getName(), zipOutputStream);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(filename);
        zipOutputStream.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOutputStream.write(bytes, 0, length);
        }
        fis.close();
    }

    private void removeDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    removeDirectory(file);
                }
            }
        }
        dir.delete();
    }
}
