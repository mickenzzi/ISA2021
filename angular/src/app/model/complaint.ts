import { User } from '../model/user';

export class Complaint {
	id?: number;
	content?: string;
	answered?: boolean;
	userComplaint: User = new User();
	instructorComplaint: User = new User();
	adminComplaint: User = new User();
}
