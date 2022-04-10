import { IUser } from 'app/entities/user/user.model';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  name?: string;
  phoneNumber?: string | null;
  gender?: Gender | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  addressLine3?: string | null;
  addressLine4?: string | null;
  townCity?: string | null;
  county?: string | null;
  zip?: string | null;
  user?: IUser | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public phoneNumber?: string | null,
    public gender?: Gender | null,
    public addressLine1?: string | null,
    public addressLine2?: string | null,
    public addressLine3?: string | null,
    public addressLine4?: string | null,
    public townCity?: string | null,
    public county?: string | null,
    public zip?: string | null,
    public user?: IUser | null
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
