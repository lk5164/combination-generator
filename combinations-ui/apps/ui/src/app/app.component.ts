import { Component, OnInit } from '@angular/core';
import { Item, Result } from './app.model';
import { AppService } from './app.service';
import { map } from 'rxjs/operators';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'combinations-ui-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  phoneNumber: string = '';
  combinations: Item[] = [];
  isLoading: boolean = false;
  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;

  constructor(private appService: AppService) {}

  ngOnInit() {
    this.listItems();
  }

  updatePageSize(pageSize: number): void {
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.listItems();
  }

  listItems() {
    this.handleListItems();
  }

  handleListItems() {
    this.appService.getItemsPaginate(this.thePageNumber - 1,
                                     this.thePageSize)
         .subscribe(this.processResult());
  }

  onExecute(form: NgForm) {
    this.isLoading = true;
    const value = form.value;

    this.appService.generateCombinations(value.phoneNumber)
            .subscribe((items: Item[]) => {
              if (!!items) {
                this.listItems();
              }
            }, error => {
              console.log('Error generating combinations!');
              console.log(error);
              this.isLoading = false;
            }, () => {
              console.log('Combinations are generated successfully!');
              this.isLoading = false;
            });
  }

  processResult() {
      return data => {
        this.combinations = data.content;
        this.thePageNumber = data.number + 1;
        this.thePageSize = data.size;
        this.theTotalElements = data.totalElements;
      };
    }
}
