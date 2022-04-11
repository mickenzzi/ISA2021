import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginDetails} from '../model/login-details';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {UserTokenState} from '../model/user-token-state';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private authUrl = 'http://localhost:8081/myApp/auth';
  private access_token: any = '';

  constructor(private http: HttpClient) {
  }

  public loginUser(loginDetails: LoginDetails): Observable<void> {
    const body = {
      username: loginDetails.username,
      password: loginDetails.password
    }
    return this.http.post<UserTokenState>(`${this.authUrl}/login`, body)
      .pipe(map((res) => {
        const username1 = body.username;
        this.access_token = res.accessToken;
        const token = this.access_token;
        localStorage.setItem('currentUser', JSON.stringify({username1, token}));
      }));
  }

  getToken(): string {
    //@ts-ignore
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }


  tokenIsPresent() {
    return this.access_token != undefined && this.access_token != null;
  }

  getCurrentUser(): any {
    if (localStorage['currentUser']) {
      return JSON.parse(localStorage['currentUser']);
    } else {
      return undefined;
    }
  }

}
