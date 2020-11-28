import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';

export interface IBankAccount {
  id?: number;
  name?: string;
  iban?: number;
  bic?: number;
  myBuddyAccount?: IMyBuddyAccount;
}

export class BankAccount implements IBankAccount {
  constructor(
    public id?: number,
    public name?: string,
    public iban?: number,
    public bic?: number,
    public myBuddyAccount?: IMyBuddyAccount
  ) {}
}
