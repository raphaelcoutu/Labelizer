import { Photo } from './photo';
import { Label } from './label';

export class Dataset {
  id?: number;
  name: string;
  photos?: Photo[];
  labels?: Label[];
}
