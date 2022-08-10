import {Cottage} from '../model/cottage';
import {User} from '../model/user';


export class CottageReservation {
  id?: number;
  start?: string;
  duration?: string;
  maxPeople?: number;
  additionalService?: string;
  price?: string;
  userReservation: User = new User();
  reservedCottage: Cottage = new Cottage();
}