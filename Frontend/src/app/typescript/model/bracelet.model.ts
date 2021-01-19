export interface BraceletDTO{
  braceletId?: number;
  length: number;
  material: string;
  color: string;
  style: string;
}

export class Bracelet{
  private _braceletId: number;
  private _length: number;
  private _material: string;
  private _style: string;
  private _color: string;


  constructor(braceletId: number, length: number, material: string, style: string, color: string) {
    this._braceletId = braceletId;
    this._length = length;
    this._material = material;
    this._style = style;
    this._color = color;
  }

  static fromRequest(request: BraceletDTO): Bracelet{
    return new Bracelet(request.braceletId, request.length, request.material, request.style, request.color);
  }


  get braceletId(): number {
    return this._braceletId;
  }

  set braceletId(value: number) {
    this._braceletId = value;
  }

  get length(): number {
    return this._length;
  }

  set length(value: number) {
    this._length = value;
  }

  get material(): string {
    return this._material;
  }

  set material(value: string) {
    this._material = value;
  }

  get style(): string {
    return this._style;
  }

  set style(value: string) {
    this._style = value;
  }

  get color(): string {
    return this._color;
  }

  set color(value: string) {
    this._color = value;
  }

  toUpdateRequest(): BraceletDTO{
    return {
      braceletId: this._braceletId,
      length: this._length,
      material: this._material,
      color: this._color,
      style: this._style
    };
  }

  toCreateRequest(): BraceletDTO{
    return {
      length: this._length,
      material: this._material,
      color: this._color,
      style: this._style
    };
  }
}
