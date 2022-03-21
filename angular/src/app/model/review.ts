import {User} from './user';

export class Review {
  id?: number;
  comment?: string;
  grade?: number;
  userReview?: User = new User();
  enabled?: boolean;
}
