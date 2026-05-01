import { apiPut } from './client';

export function clockIn(userID: number, time: string): Promise<string> {
  return apiPut<string>(`/user_timecard/${userID}/clockIn`, time);
}

export function clockOut(userID: number, time: string, reason: string): Promise<string> {
  return apiPut<string>(`/user_timecard/${userID}/clockOut?reason=${encodeURIComponent(reason)}`, time);
}

export function updateWorkedHours(userID: number, hours: number): Promise<string> {
  return apiPut<string>(`/user_timecard/${userID}/worked_hours/${hours}`);
}
