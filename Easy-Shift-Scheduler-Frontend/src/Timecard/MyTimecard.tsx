import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { getTimecard } from '../api/user';
import type { UserTimecard } from '../types';
import '../components/CSS/Layout.css';
import './CSS/Timecard.css';

function formatDateTime(dt: string): string {
  try {
    return new Date(dt).toLocaleString();
  } catch {
    return dt;
  }
}

export default function MyTimecard() {
  const { user } = useAuth();
  const [timecard, setTimecard] = useState<UserTimecard | null>(null);
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function fetchTimecard() {
    if (!user) return;
    setLoading(true);
    setStatus('');
    try {
      const data = await getTimecard(user.id);
      setTimecard(data);
      if (!data) setStatus('No timecard found.');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to load timecard.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">My Timecard</h1>

      <div className="card">
        <h2 className="card-title">Hours Summary</h2>
        <button className="btn btn-primary" onClick={fetchTimecard} disabled={loading} style={{ marginBottom: 20 }}>
          {loading ? 'Loading...' : 'Load My Timecard'}
        </button>

        {timecard && (
          <>
            <div style={{ display: 'flex', gap: 16, marginBottom: 24 }}>
              <div style={{ background: '#384959', borderRadius: 10, padding: '16px 24px', minWidth: 140 }}>
                <p className="worked-hours-display">{timecard.worked_hours ?? 0}</p>
                <p className="worked-hours-label">Total Hours Worked</p>
              </div>
              <div style={{ background: '#384959', borderRadius: 10, padding: '16px 24px', minWidth: 140 }}>
                <p className="worked-hours-display">{timecard.punch_times?.length ?? 0}</p>
                <p className="worked-hours-label">Total Punches</p>
              </div>
            </div>

            <h3 style={{ fontSize: 16, fontWeight: 600, color: '#384959', margin: '0 0 12px' }}>Punch History</h3>
            {timecard.punch_times && timecard.punch_times.length > 0 ? (
              <div className="punch-list">
                {timecard.punch_times.map((punch, i) => (
                  <div className="punch-item" key={punch.id ?? i}>
                    <span className="punch-index">#{i + 1}</span>
                    <span className="punch-time">{formatDateTime(punch.punch_time)}</span>
                    {punch.reason && <span className="punch-reason">{punch.reason}</span>}
                  </div>
                ))}
              </div>
            ) : (
              <div className="empty-state"><p>No punch records found.</p></div>
            )}
          </>
        )}

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>
    </div>
  );
}
