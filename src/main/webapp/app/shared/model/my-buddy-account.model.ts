import { IUser } from '@/shared/model/user.model';
import { IBankAccount } from '@/shared/model/bank-account.model';
import { IMyTransaction } from '@/shared/model/my-transaction.model';

export interface IMyBuddyAccount {
  id?: number;
  balance?: number;
  profilePicture?: string;
  user?: IUser;
  bankAccount?: IBankAccount;
  myTransactions?: IMyTransaction[];
}

export class MyBuddyAccount implements IMyBuddyAccount {
  constructor(
    public id?: number,
    public balance?: number,
    public profilePicture?: string,
    public user?: IUser,
    public bankAccount?: IBankAccount,
    public myTransactions?: IMyTransaction[]
  ) {}
}
