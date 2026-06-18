import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Account } from "../../model/account";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) {
  }

  getAccounts(): Observable<Account[]> {
    return this.httpClient.get<Account[]>(
      'http://localhost:10000/api/account'
    );
  }

  createAccount(account: Account): Observable<Account> {
    return this.httpClient.post<Account>(
      'http://localhost:10000/api/account',
      account
    );
  }
}
