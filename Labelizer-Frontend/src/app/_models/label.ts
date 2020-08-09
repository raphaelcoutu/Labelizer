import { Dataset } from './dataset';

export class Label {
  id?: number;
  dataset: Dataset;
  name: string;

  constructor() {}

  toString() {
    return this.name;
  }
}
