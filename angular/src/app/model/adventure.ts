import { User } from '../model/user';
export class Adventure {
	id?: number;
	title?: string;
	description?: string;
	address?: string;
	image?: string;
	maxNumber?: number;
	instructorBiography?: string;
	rule?: string;
	equipment?: string;
	priceList?: number;
	cancelCondition?: string;
	reserved?: boolean;
}
