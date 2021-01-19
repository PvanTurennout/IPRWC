import {Bracelet, BraceletDTO} from './bracelet.model';

export interface WatchDTO{
  watchId?: number;
  size: number;
  material: string;
  colorPointer: string;
  colorDial: string;
  watchBracelet: BraceletDTO;
}

  export class Watch{

  private readonly _watchId: number;
  private _watchBracelet: Bracelet;
  private _material: string;
  private _size: number;
  private _colorPointer: string;
  private _colorDial: string;


  constructor(watchId: number, material: string, size: number, colorPointer: string, colorDial: string, watchBracelet: Bracelet) {
    this._watchId = watchId;
    this._watchBracelet = watchBracelet;
    this._material = material;
    this._size = size;
    this._colorPointer = colorPointer;
    this._colorDial = colorDial;
  }

  static fromRequest(request: WatchDTO){
    return new Watch(request.watchId, request.material, request.size, request.colorPointer,
                     request.colorDial, Bracelet.fromRequest(request.watchBracelet));
  }


  get watchId(): number {
    return this._watchId;
  }

  get watchBracelet(): Bracelet {
    return this._watchBracelet;
  }

  set watchBracelet(value: Bracelet) {
    this._watchBracelet = value;
  }

  get material(): string {
    return this._material;
  }

  set material(value: string) {
    this._material = value;
  }

  get size(): number {
    return this._size;
  }

  set size(value: number) {
    this._size = value;
  }

  get colorPointer(): string {
    return this._colorPointer;
  }

  set colorPointer(value: string) {
    this._colorPointer = value;
  }

  get colorDial(): string {
    return this._colorDial;
  }

  set colorDial(value: string) {
    this._colorDial = value;
  }

  toUpdateRequest(): WatchDTO {
    return {
      watchId: this._watchId,
      size: this._size,
      material: this._material,
      colorPointer: this._colorPointer,
      colorDial: this._colorDial,
      watchBracelet: this._watchBracelet.toUpdateRequest()
    };
  }

    toCreateRequest(): WatchDTO {
      return {
        size: this._size,
        material: this._material,
        colorPointer: this._colorPointer,
        colorDial: this._colorDial,
        watchBracelet: this._watchBracelet.toCreateRequest()
      };
    }
}
