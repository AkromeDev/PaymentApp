import { IContactRelationship } from '@/shared/model/contact-relationship.model';
import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';

export interface IMyTransaction {
  id?: number;
  date?: Date;
  amount?: number;
  description?: string;
  contactRelationship?: IContactRelationship;
  myBuddyAccount?: IMyBuddyAccount;
}

export class MyTransaction implements IMyTransaction {
  constructor(
    public id?: number,
    public date?: Date,
    public amount?: number,
    public description?: string,
    public contactRelationship?: IContactRelationship,
    public myBuddyAccount?: IMyBuddyAccount
  ) {}
}
