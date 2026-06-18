export interface Transaction {
  id?: number;
  fromAccount: string;
  toAccount: string;
  amount: number;
  transactionType: string;
}
