import { apiGet, apiPost, apiPut } from './client';
import type { TimeOffRequest } from '../types';

export function requestTimeOff(userID: number, req: Omit<TimeOffRequest, 'id' | 'approved' | 'user_that_requested'>): Promise<string> {
  return apiPost<string>(`/time-off-request/${userID}/request-time-off`, req);
}

export function getTimeOffRequests(): Promise<string> {
  return apiGet<string>('/time-off-request/view-time-off-requests');
}

export function approveTimeOffRequest(requestID: number): Promise<string> {
  return apiPut<string>(`/time-off-request/${requestID}/approve`);
}

export function denyTimeOffRequest(requestID: number): Promise<string> {
  return apiPut<string>(`/time-off-request/${requestID}/deny`);
}
