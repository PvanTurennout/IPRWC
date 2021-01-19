interface AccountDTO {
  mailAddress: string;
  password: string;
}

export class Account {
  private _email: string;
  private _password: string;

  constructor(mail: string, password: string) {
    this._email = mail;
    this._password = password;
  }


  static fromRequest(accountDTO: AccountDTO) {
    return new Account(accountDTO.mailAddress, accountDTO.password);
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  toRequest(): AccountDTO {
    return {mailAddress: this._email, password: this._password};
  }
}
