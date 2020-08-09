import { Label } from './label';

export class LabelBox {
  id?: number;
  x: number;
  y: number;
  width: number;
  height: number;
  label: Label;

  constructor(x: number, y: number, width: number, height: number, label: Label){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.label = label;`d`
  }

  toString() {
    return 'x:' + this.x + ', y:' + this.y + ', w:' + this.width + 'h:' + this.height;
  }
}
