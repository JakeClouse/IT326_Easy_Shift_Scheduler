import { useState } from 'react';
import { Link } from 'react-router-dom';
import { createSchedule, updateSchedule, deleteWorkSchedule, createAutoSchedule } from '../api/schedule';
import '../components/CSS/Layout.css';
import './CSS/Schedule.css';

export default function EmployerSchedule() {
  const [employeeID, setEmployeeID] = useState('');
  const [scheduleID, setScheduleID] = useState('');
  const [times, setTimes] = useState<string[]>(['', '']);
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const [mode, setMode] = useState<'create' | 'update' | 'delete' | 'auto'>('create');

  function addTime() {
    setTimes(t => [...t, '']);
  }

  function removeTime(i: number) {
    setTimes(t => t.filter((_, idx) => idx !== i));
  }

  function setTime(i: number, val: string) {
    setTimes(t => t.map((v, idx) => idx === i ? val : v));
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setStatus('');
    setLoading(true);
    try {
      let msg: string;
      if (mode === 'create') {
        const filtered = times.filter(Boolean);
        await createSchedule(Number(employeeID), filtered);
        msg = 'Schedule created successfully.';
      } else if (mode === 'update') {
        const filtered = times.filter(Boolean);
        await updateSchedule(Number(scheduleID), filtered);
        msg = 'Schedule updated successfully.';
      } else if (mode === 'delete') {
        msg = await deleteWorkSchedule(Number(employeeID));
      } else {
        msg = await createAutoSchedule(Number(employeeID));
      }
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Operation failed.');
    } finally {
      setLoading(false);
    }
  }

  const showTimes = mode === 'create' || mode === 'update';
  const showScheduleID = mode === 'update';
  const modeLabels = { create: 'Create Schedule', update: 'Update Schedule', delete: 'Delete Schedule', auto: 'Auto-Generate Schedule' };

  return (
    <div className="page-container">
      <h1 className="page-title">Manage Schedules</h1>

      <div className="card">
        <h2 className="card-title">Schedule Operations</h2>

        <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap', marginBottom: 20 }}>
          {(Object.keys(modeLabels) as Array<keyof typeof modeLabels>).map(m => (
            <button
              key={m}
              className={`btn ${mode === m ? 'btn-primary' : 'btn-secondary'}`}
              onClick={() => { setMode(m); setStatus(''); }}
            >
              {modeLabels[m]}
            </button>
          ))}
        </div>

        <form onSubmit={handleSubmit}>
          {showScheduleID && (
            <div className="form-group">
              <label className="form-label">Schedule ID (to update)</label>
              <input
                className="form-input"
                type="number"
                placeholder="Enter schedule ID"
                value={scheduleID}
                onChange={e => setScheduleID(e.target.value)}
                required
              />
            </div>
          )}

          {mode !== 'update' && (
            <div className="form-group">
              <label className="form-label">Employee ID</label>
              <input
                className="form-input"
                type="number"
                placeholder="Enter employee ID"
                value={employeeID}
                onChange={e => setEmployeeID(e.target.value)}
                required
              />
            </div>
          )}

          {showTimes && (
            <div className="form-group">
              <label className="form-label">Shift Times (pairs of start/end)</label>
              <div className="schedule-times">
                {times.map((t, i) => (
                  <div className="schedule-time-entry" key={i}>
                    <input
                      type="datetime-local"
                      value={t}
                      onChange={e => setTime(i, e.target.value)}
                    />
                    {times.length > 2 && (
                      <button type="button" className="remove-time-btn" onClick={() => removeTime(i)}>×</button>
                    )}
                  </div>
                ))}
              </div>
              <button type="button" className="add-time-btn" onClick={addTime}>+ Add Time Slot</button>
            </div>
          )}

          {mode === 'auto' && (
            <p style={{ fontSize: 13, color: '#5a7896', marginBottom: 12 }}>
              Auto-generate a work schedule from the employee's availability.
            </p>
          )}

          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Processing...' : modeLabels[mode]}
          </button>
        </form>

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>

      <div className="card">
        <h2 className="card-title">Quick Links</h2>
        <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
          <Link to="/groups" className="btn btn-secondary" style={{ textDecoration: 'none' }}>Manage Groups</Link>
          <Link to="/time-off/manage" className="btn btn-secondary" style={{ textDecoration: 'none' }}>Review Time Off</Link>
        </div>
      </div>
    </div>
  );
}
