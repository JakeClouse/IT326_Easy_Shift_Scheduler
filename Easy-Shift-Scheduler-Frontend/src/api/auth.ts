import { apiPost } from './client';
import type { JwtResponse, SignInRequest, SignUpRequest } from '../types';

export function signIn(req: SignInRequest): Promise<JwtResponse> {
  return apiPost<JwtResponse>('/auth/signin', req);
}

export function employeeSignUp(req: SignUpRequest): Promise<string> {
  return apiPost<string>('/auth/employee_signup', req);
}

export function employerSignUp(req: SignUpRequest): Promise<string> {
  return apiPost<string>('/auth/employer_signup', req);
}
