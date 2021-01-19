export class RequestFailedException implements Error {
  name = 'RequestFailedException';

  constructor(public message: string) {
    if (typeof console !== undefined) {
      console.log(`Error: ${message}`);
    }
  }

  toString(): string {
    return `${this.name} : ${this.message}`;
  }
}
