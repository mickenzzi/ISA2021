import { User } from "./user";

export class TerminBoat {
    id?: number;
    reserved?: boolean;
    daysDuration?: number;
    start?: string;
    end?: string;
    action?: boolean;
    price?: number;
    capacity?: number;
    userReserved?: User;
  }