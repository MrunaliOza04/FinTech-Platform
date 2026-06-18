import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { NgIf } from '@angular/common';

import { Account } from '../../model/account';
import { AccountService } from '../../services/account/account.service';

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './add-account.component.html',
  styleUrl: './add-account.component.css'
})
export class AddAccountComponent {

  createAccountForm: FormGroup;

  private readonly accountService = inject(AccountService);

  accountCreated = false;

  constructor(private fb: FormBuilder) {

    this.createAccountForm = this.fb.group({
      accountNumber: ['', [Validators.required]],
      customerName: ['', [Validators.required]],
      accountType: ['', [Validators.required]],
      balance: [0, [Validators.required]]
    });

  }

  onSubmit(): void {

    if (this.createAccountForm.valid) {

      const account: Account = {
        accountNumber: this.createAccountForm.get('accountNumber')?.value,
        customerName: this.createAccountForm.get('customerName')?.value,
        accountType: this.createAccountForm.get('accountType')?.value,
        balance: this.createAccountForm.get('balance')?.value
      };

      this.accountService.createAccount(account)
        .subscribe(account => {

          this.accountCreated = true;
          this.createAccountForm.reset();

        });

    } else {

      console.log('Form is not valid');

    }

  }

  get accountNumber() {
    return this.createAccountForm.get('accountNumber');
  }

  get customerName() {
    return this.createAccountForm.get('customerName');
  }

  get accountType() {
    return this.createAccountForm.get('accountType');
  }

  get balance() {
    return this.createAccountForm.get('balance');
  }

}
