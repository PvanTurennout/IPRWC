export class CantBeNullError implements Error {
  name = 'CantBeNullError';

  constructor(public message: string) {
    if (typeof console !== undefined) {
      console.log(`Error: ${message}`);
    }
  }

  toString(): string {
    return `${this.name} : ${this.message}`;
  }
}
