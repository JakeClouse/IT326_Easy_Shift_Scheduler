import { apiGet, apiPost } from './client';
import type { DroppedShift } from '../types';

export function getDroppedShifts(): Promise<string> {
  return apiGet<string>('/dropped-shift/view-dropped-shifts');
}

export function dropShift(userID: number, shift: Omit<DroppedShift, 'id' | 'user_that_requested'>): Promise<string> {
  return apiPost<string>(`/dropped-shift/${userID}/drop-shift`, shift);
}
