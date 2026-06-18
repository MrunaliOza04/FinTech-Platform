import { Component, inject, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { AsyncPipe, JsonPipe } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { Account } from '../../model/account';
import { Transaction } from '../../model/transaction';

import { AccountService } from '../../services/account/account.service';
import { TransactionService } from '../../services/transaction/transaction.service';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {

  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly accountService = inject(AccountService);
  private readonly transactionService = inject(TransactionService);
  private readonly router = inject(Router);

  isAuthenticated = false;

  accounts: Array<Account> = [];

  amountIsNull = false;
  transactionSuccess = false;
  transactionFailed = false;

  ngOnInit(): void {

    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {

        this.isAuthenticated = isAuthenticated;

        this.accountService.getAccounts()
          .subscribe(accounts => {
            this.accounts = accounts;
          });

      }
    );
  }

  goToCreateAccountPage(): void {
    this.router.navigateByUrl('/add-account');
  }

  transferMoney(account: Account, amount: string): void {

    if (!amount) {
      this.transactionFailed = true;
      this.transactionSuccess = false;
      this.amountIsNull = true;
      return;
    }

    const transaction: Transaction = {
      fromAccount: account.accountNumber,
      toAccount: '1002',
      amount: Number(amount),
      transactionType: 'TRANSFER'
    };

    this.transactionService.createTransaction(transaction)
      .subscribe({
        next: () => {
          this.transactionSuccess = true;
          this.transactionFailed = false;
          this.amountIsNull = false;
        },
        error: () => {
          this.transactionSuccess = false;
          this.transactionFailed = true;
        }
      });
  }
}
