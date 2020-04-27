export interface Item {
  id: number;
  text: string;
}

export interface Result {
  _embedded: {
      items: Item[];
    },
    page: {
      size: number,
      totalElements: number,
      totalPages: number,
      number: number
    }
}
