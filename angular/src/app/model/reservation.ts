import {Adventure} from '../model/adventure';
import {User} from '../model/user';


export class Reservation {
  id?: number;
  start?: string;
  end?: string;
  createdReservation?: boolean;
  userReservation: User = new User();
  adventureReservation: Adventure = new Adventure();
}
