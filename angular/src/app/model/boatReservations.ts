import {Boat} from '../model/boat';
import {User} from '../model/user';


export class BoatReservation {
  id?: number;
  start?: string;
  duration?: string;
  maxPeople?: number;
  additionalService?: string;
  price?: string;
  userReservation: User = new User();
  reservedBoat: Boat = new Boat();
}