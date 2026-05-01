export interface JwtResponse {
  token: string;
  id: number;
  username: string;
  email: string;
  roles: string[];
}

export interface AuthUser {
  id: number;
  username: string;
  email: string;
  roles: string[];
}

export interface UserWorkSchedule {
  id: number;
  work_schedule: string[];
}

export interface UserAvailabilitySchedule {
  id: number;
  availability_schedule: string[];
}

export interface Punch {
  id: number;
  punch_time: string;
  reason: string;
}

export interface UserTimecard {
  id: number;
  punch_times: Punch[];
  worked_hours: number;
}

export interface DroppedShift {
  id: number;
  startDate: string;
  endDate: string;
  reason: string;
  user_that_requested?: { id: number; username: string };
}

export interface TimeOffRequest {
  id: number;
  startDate: string;
  endDate: string;
  reason: string;
  approved: boolean;
  user_that_requested?: { id: number; username: string };
}

export interface Group {
  id: number;
  users: { id: number; username: string; email: string }[];
}

export interface SignInRequest {
  username: string;
  password: string;
}

export interface SignUpRequest {
  username: string;
  email: string;
  password: string;
}
