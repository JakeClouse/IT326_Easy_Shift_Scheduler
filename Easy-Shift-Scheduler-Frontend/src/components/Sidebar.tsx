import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './CSS/Sidebar.css';

export default function Sidebar() {
  const { user, isEmployer, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate('/login');
  }

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h1>Easy Shift Scheduler</h1>
      </div>

      {user && (
        <div className="sidebar-user">
          <p className="sidebar-user-name">{user.username}</p>
          <p className="sidebar-user-role">{isEmployer ? 'Employer' : 'Employee'}</p>
        </div>
      )}

      <nav className="sidebar-nav">
        <p className="sidebar-section-label">General</p>
        <NavLink to="/dashboard" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
          Dashboard
        </NavLink>
        <NavLink to="/account" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
          Account Settings
        </NavLink>

        <p className="sidebar-section-label">Schedule</p>
        <NavLink to="/schedule" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
          {isEmployer ? 'Manage Schedules' : 'My Schedule'}
        </NavLink>
        {!isEmployer && (
          <NavLink to="/availability" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
            Set Availability
          </NavLink>
        )}

        <p className="sidebar-section-label">Shifts</p>
        <NavLink to="/shifts" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
          Dropped Shifts
        </NavLink>
        {!isEmployer && (
          <NavLink to="/drop-shift" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
            Drop a Shift
          </NavLink>
        )}

        <p className="sidebar-section-label">Time</p>
        {!isEmployer && (
          <>
            <NavLink to="/clock" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
              Clock In / Out
            </NavLink>
            <NavLink to="/timecard" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
              My Timecard
            </NavLink>
            <NavLink to="/time-off" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
              Request Time Off
            </NavLink>
          </>
        )}
        {isEmployer && (
          <NavLink to="/time-off" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
            Time Off Requests
          </NavLink>
        )}

        {isEmployer && (
          <>
            <p className="sidebar-section-label">Management</p>
            <NavLink to="/groups" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
              Groups
            </NavLink>
          </>
        )}
      </nav>

      <div className="sidebar-footer">
        <button className="sidebar-logout" onClick={handleLogout}>
          Sign Out
        </button>
      </div>
    </aside>
  );
}
