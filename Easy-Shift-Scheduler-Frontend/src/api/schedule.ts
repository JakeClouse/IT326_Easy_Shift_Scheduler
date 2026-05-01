import { apiGet, apiPost, apiPut } from './client';
import type { UserAvailabilitySchedule, UserWorkSchedule } from '../types';

export function getAvailSchedule(userID: number): Promise<string> {
  return apiGet<string>(`/schedule/${userID}/avail-schedule`);
}

export function setAvailSchedule(userID: number, schedule: UserAvailabilitySchedule): Promise<string> {
  return apiPut<string>(`/schedule/${userID}/avail-schedule/update`, schedule);
}

export function createSchedule(userID: number, times: string[]): Promise<UserWorkSchedule> {
  const params = new URLSearchParams();
  params.set('userID', String(userID));
  times.forEach(t => params.append('times', t));
  return apiPost<UserWorkSchedule>(`/schedule/create-schedule?${params}`);
}

export function updateSchedule(workScheduleID: number, times: string[]): Promise<UserWorkSchedule> {
  const params = new URLSearchParams();
  params.set('UserWorkScheduleID', String(workScheduleID));
  times.forEach(t => params.append('times', t));
  return apiPost<UserWorkSchedule>(`/schedule/update-schedule?${params}`);
}

export function acknowledgeSchedule(userID: number, employerID: number): Promise<string> {
  return apiPost<string>(`/schedule/acknowledge-schedule?UserID=${userID}&EmployerID=${employerID}`);
}

export function deleteWorkSchedule(userID: number): Promise<string> {
  return apiPut<string>(`/schedule/${userID}/work-schedule/delete`);
}

export function createAutoSchedule(userID: number): Promise<string> {
  return apiPost<string>(`/schedule/${userID}/work-schedule/auto`);
}

export function pickupShift(userID: number, shiftID: number): Promise<string> {
  return apiPut<string>(`/schedule/${userID}/pickup-shift?shiftID=${shiftID}`);
}
