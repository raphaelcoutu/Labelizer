import { Dataset } from './dataset';
import { LabelBox } from './label-box';

export class Photo {
  id?: number;
  dataset?: Dataset;
  filename: string;
  extension: string;
  originalFilename: string;
  originalExtension: string;
  verified: boolean;
  labelBoxes?: LabelBox[];
  createdAt: Date;

  constructor(id: number, dataset: Dataset, filename: string, originalFilename: string) {
    this.id = id;
    this.dataset = dataset;
    this.filename = filename;
    this.originalFilename = originalFilename;
  }
}
