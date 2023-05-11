export interface PageDTO<T> {
  items: T[],
  pageIndex: number,
  pageSize: number,
  totalElements: number,
}
