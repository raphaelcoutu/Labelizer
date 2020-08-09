import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, of } from 'rxjs';
import { Photo } from '../_models/photo';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {
  baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll() {
    return []
  }

  getAllForDataset(datasetId: number): Observable<Photo[]> {
    return this.http.get<Photo[]>(`${this.baseUrl}/datasets/${datasetId}/photos`);
  }

  get(photoId: number): Observable<Photo> {
    return this.http.get<Photo>(`${this.baseUrl}/photos/${photoId}`);
  }

  getNextUnverified(datasetId: number): Observable<Photo> {
    return this.http.get<Photo>(`${this.baseUrl}/datasets/${datasetId}/nextUnverified`);
  }

  getNextUnverifiedExcept(datasetId: number, exceptId: number): Observable<Photo> {
    let httpParams = new HttpParams().append('exceptId', exceptId.toString());

    return this.http.get<Photo>(`${this.baseUrl}/datasets/${datasetId}/nextUnverified`, { params: httpParams });
  }

  put(id: number, photo: Photo) {
    return this.http.put<Photo>(`${this.baseUrl}/photos/${id}`, photo);
  }

  delete(id: number) {
    return this.http.delete<Photo>(`${this.baseUrl}/photos/${id}`);
  }

}
