import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/Layout';
import LoginPage from './LoginPage/LoginPage';
import RegisterPage from './RegisterPage/RegisterPage';
import Dashboard from './Dashboard/Dashboard';
import Schedule from './Schedule/Schedule';
import SetAvailability from './Schedule/SetAvailability';
import CreateSchedule from './Schedule/CreateSchedule';
import DroppedShifts from './DroppedShifts/DroppedShifts';
import DropShift from './DroppedShifts/DropShift';
import RequestTimeOff from './TimeOff/RequestTimeOff';
import ManageTimeOff from './TimeOff/ManageTimeOff';
import MyTimecard from './Timecard/MyTimecard';
import ClockInOut from './Timecard/ClockInOut';
import AccountSettings from './Account/AccountSettings';
import Groups from './Groups/Groups';

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          <Route
            element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/account" element={<AccountSettings />} />
            <Route path="/shifts" element={<DroppedShifts />} />

            {/* Employee-only */}
            <Route path="/availability" element={<SetAvailability />} />
            <Route path="/drop-shift" element={<DropShift />} />
            <Route path="/clock" element={<ClockInOut />} />
            <Route path="/timecard" element={<MyTimecard />} />
            <Route path="/time-off" element={<RequestTimeOff />} />

            {/* Role-based schedule */}
            <Route path="/schedule" element={<Schedule />} />

            {/* Employer-only */}
            <Route
              path="/schedule/create"
              element={
                <ProtectedRoute requireEmployer>
                  <CreateSchedule />
                </ProtectedRoute>
              }
            />
            <Route
              path="/time-off/manage"
              element={
                <ProtectedRoute requireEmployer>
                  <ManageTimeOff />
                </ProtectedRoute>
              }
            />
            <Route
              path="/groups"
              element={
                <ProtectedRoute requireEmployer>
                  <Groups />
                </ProtectedRoute>
              }
            />
          </Route>

          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}
