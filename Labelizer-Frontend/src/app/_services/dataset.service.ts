import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Dataset } from '../_models/dataset';

@Injectable({
  providedIn: 'root'
})
export class DatasetService {

  baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Dataset[]>(`${this.baseUrl}/datasets`);
  }
}
