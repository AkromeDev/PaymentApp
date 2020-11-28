import { IUser } from '@/shared/model/user.model';

export interface IContactRelationship {
  id?: number;
  userId1?: number;
  userId2?: number;
  users?: IUser[];
}

export class ContactRelationship implements IContactRelationship {
  constructor(public id?: number, public userId1?: number, public userId2?: number, public users?: IUser[]) {}
}
