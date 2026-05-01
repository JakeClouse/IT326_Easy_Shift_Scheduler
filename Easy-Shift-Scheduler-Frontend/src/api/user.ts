import { apiGet, apiPut, apiDelete } from './client';
import type { UserTimecard } from '../types';

export function getTimecard(userID: number): Promise<UserTimecard> {
  return apiGet<UserTimecard>(`/user/${userID}/user_timecard`);
}

export function updateAccountInfo(userID: number, body: { username: string; email: string }): Promise<string> {
  return apiPut<string>(`/user/${userID}/account/update`, body);
}

export function updatePassword(userID: number, newPassword: string): Promise<string> {
  return apiPut<string>(`/user/${userID}/password/update`, newPassword);
}

export function deleteAccount(userID: number): Promise<string> {
  return apiDelete<string>(`/user/${userID}/account/delete`);
}

export function updateCompensationRate(userID: number, rate: number): Promise<string> {
  return apiPut<string>(`/user/${userID}/compensation-rate`, rate);
}

export function getCompensationReport(userID: number): Promise<string> {
  return apiGet<string>(`/user/${userID}/compensation-report`);
}

export function assignPunchReason(punchID: number, reason: string): Promise<unknown> {
  return apiPut(`/user/${punchID}/reason`, reason);
}
