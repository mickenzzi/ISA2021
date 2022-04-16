import { Injectable } from '@angular/core'
import { Cottage } from '../model/cottage';


@Injectable()
export class CottageService {

    getCottages(): Cottage[] {
        return[]
    }
}