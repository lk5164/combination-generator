import { Injectable } from '@angular/core';
import { Item, Result } from './app.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AppService {
  constructor(private http: HttpClient) {}

  generateCombinations(phoneNumber: string): Observable<Item[]> {
    return this.http.post<Item[]>(
      '/reference/v1/generate',
      phoneNumber
    );
  }

  getItemsPaginate(thePage: number, thePageSize: number): Observable<Result> {
      return this.http.get<Result>(
        'reference/v1/getItems/size/' + thePageSize + '/page/' + thePage
      );
   }

   initDB(): Observable<void> {
      return this.http.delete<void>(
        'reference/v1/reset'
      );
   }
}
