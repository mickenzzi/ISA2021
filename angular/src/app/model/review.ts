import { User } from '../model/user';

export class Review {
	id?: number;
	comment?: string;
	grade?: number;
	userReview?: User = new User();
	instructorReview?: User = new User();
}
