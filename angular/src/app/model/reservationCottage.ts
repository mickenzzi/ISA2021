import {Cottage} from '../model/cottage';

export class Reservation {
  id?: number;
  start?: string;
  end?: string;
  createdReservation?: boolean;
  price?: number;
  adventureReservation: Cottage = new Cottage();
}
