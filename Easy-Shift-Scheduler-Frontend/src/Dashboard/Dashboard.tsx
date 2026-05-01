import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../components/CSS/Layout.css';
import './CSS/Dashboard.css';

export default function Dashboard() {
  const { user, isEmployer } = useAuth();

  return (
    <div className="page-container">
      <h1 className="page-title">Dashboard</h1>
      <p className="dashboard-welcome">Welcome back, {user?.username}!</p>

      {isEmployer ? <EmployerDashboard /> : <EmployeeDashboard />}
    </div>
  );
}

function EmployeeDashboard() {
  return (
    <>
      <div className="dashboard-grid">
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Hours This Week</p>
        </div>
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Upcoming Shifts</p>
        </div>
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Pending Requests</p>
        </div>
      </div>

      <div className="dashboard-grid">
        <div className="action-card">
          <h3>Clock In / Out</h3>
          <p>Record your work hours by clocking in and out of your shifts.</p>
          <Link to="/clock" className="quick-action-link">Go to Clock</Link>
        </div>
        <div className="action-card">
          <h3>My Schedule</h3>
          <p>View your upcoming work schedule and acknowledge shifts.</p>
          <Link to="/schedule" className="quick-action-link">View Schedule</Link>
        </div>
        <div className="action-card">
          <h3>Set Availability</h3>
          <p>Update when you are available to work each week.</p>
          <Link to="/availability" className="quick-action-link">Set Availability</Link>
        </div>
        <div className="action-card">
          <h3>Request Time Off</h3>
          <p>Submit a time off request for approval by your employer.</p>
          <Link to="/time-off" className="quick-action-link">Request Time Off</Link>
        </div>
        <div className="action-card">
          <h3>Dropped Shifts</h3>
          <p>Pick up available shifts dropped by other employees.</p>
          <Link to="/shifts" className="quick-action-link">View Shifts</Link>
        </div>
        <div className="action-card">
          <h3>My Timecard</h3>
          <p>Review your punch history and total hours worked.</p>
          <Link to="/timecard" className="quick-action-link">View Timecard</Link>
        </div>
      </div>
    </>
  );
}

function EmployerDashboard() {
  return (
    <>
      <div className="dashboard-grid">
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Active Employees</p>
        </div>
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Pending Time Off</p>
        </div>
        <div className="stat-card">
          <p className="stat-value">--</p>
          <p className="stat-label">Open Shifts</p>
        </div>
      </div>

      <div className="dashboard-grid">
        <div className="action-card">
          <h3>Manage Schedules</h3>
          <p>Create, update, and publish work schedules for your employees.</p>
          <Link to="/schedule" className="quick-action-link">Manage Schedules</Link>
        </div>
        <div className="action-card">
          <h3>Time Off Requests</h3>
          <p>Review and approve or deny employee time off requests.</p>
          <Link to="/time-off/manage" className="quick-action-link">Review Requests</Link>
        </div>
        <div className="action-card">
          <h3>Dropped Shifts</h3>
          <p>View shifts employees have dropped and need coverage.</p>
          <Link to="/shifts" className="quick-action-link">View Shifts</Link>
        </div>
        <div className="action-card">
          <h3>Groups</h3>
          <p>Create and manage employee groups for schedule publishing.</p>
          <Link to="/groups" className="quick-action-link">Manage Groups</Link>
        </div>
      </div>
    </>
  );
}
