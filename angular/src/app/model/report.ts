import { TerminCottage } from "./terminCottage";
import { User } from "./user";

export class Report {
    id?: number;
    comment?: string;
    sanctioned?: boolean;
    approved?: boolean;
    term?: TerminCottage;
    missedTerm?: boolean;
    owner?: User;
}