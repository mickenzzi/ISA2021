import {User} from '../model/user';

export class Complaint {
  id?: number;
  content?: string;
  answered?: boolean;
  adminComplaint: User = new User();
}
