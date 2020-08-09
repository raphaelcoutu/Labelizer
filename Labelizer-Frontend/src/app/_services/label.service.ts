import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, of } from 'rxjs';
import { Label } from '../_models/label';

@Injectable({
  providedIn: 'root'
})
export class LabelService {
  baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll() {
    return []
  }

  getAllForDataset(datasetId: number): Observable<Label[]> {
    return this.http.get<Label[]>(`${this.baseUrl}/datasets/${datasetId}/labels`);
  }

  put(id: number, label: Label) {
    return this.http.put<Label>(`${this.baseUrl}/labels/${id}`, label);
  }

  post(label: Label) {
    return this.http.post<Label>(`${this.baseUrl}/labels/`, label);
  }


}
