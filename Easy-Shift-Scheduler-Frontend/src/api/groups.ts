import { apiPost, apiPut, apiDelete } from './client';
import type { Group } from '../types';

export function createGroup(userIDs: number[]): Promise<Group> {
  const params = new URLSearchParams();
  userIDs.forEach(id => params.append('userIDs', String(id)));
  return apiPost<Group>(`/group/create-group?${params}`);
}

export function joinGroup(userID: number, groupID: number): Promise<string> {
  return apiPut<string>(`/group/${userID}/groups/join/${groupID}`);
}

export function removeUserFromGroup(groupID: number, employeeID: number, employerID: number): Promise<string> {
  return apiDelete<string>(`/group/remove-user-from-group?groupID=${groupID}&employeeID=${employeeID}&employerID=${employerID}`);
}
