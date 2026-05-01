import { useState } from 'react';
import { getTimeOffRequests, approveTimeOffRequest, denyTimeOffRequest } from '../api/timeOff';
import '../components/CSS/Layout.css';
import './CSS/TimeOff.css';

export default function ManageTimeOff() {
  const [requests, setRequests] = useState<string | null>(null);
  const [requestID, setRequestID] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function fetchRequests() {
    setLoading(true);
    setStatus('');
    try {
      const data = await getTimeOffRequests();
      setRequests(data);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to load requests.');
    } finally {
      setLoading(false);
    }
  }

  async function handleApprove() {
    if (!requestID) return;
    setLoading(true);
    setStatus('');
    try {
      const msg = await approveTimeOffRequest(Number(requestID));
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to approve.');
    } finally {
      setLoading(false);
    }
  }

  async function handleDeny() {
    if (!requestID) return;
    setLoading(true);
    setStatus('');
    try {
      const msg = await denyTimeOffRequest(Number(requestID));
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to deny.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Manage Time Off Requests</h1>

      <div className="card">
        <h2 className="card-title">All Requests</h2>
        <button className="btn btn-primary" onClick={fetchRequests} disabled={loading}>
          {loading ? 'Loading...' : 'Load All Requests'}
        </button>

        {requests !== null && (
          <div style={{ marginTop: 16, padding: 14, background: '#a8c8e8', borderRadius: 6, fontSize: 14, color: '#384959' }}>
            <strong>Time Off Requests:</strong>
            <pre style={{ margin: '8px 0 0', whiteSpace: 'pre-wrap', wordBreak: 'break-word', fontFamily: 'inherit' }}>
              {requests || 'No requests found.'}
            </pre>
          </div>
        )}
      </div>

      <div className="card">
        <h2 className="card-title">Approve / Deny Request</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Enter the request ID from the list above to approve or deny it.
        </p>

        <div className="form-group">
          <label className="form-label" htmlFor="requestID">Request ID</label>
          <input
            id="requestID"
            className="form-input"
            type="number"
            placeholder="Enter request ID"
            value={requestID}
            onChange={e => setRequestID(e.target.value)}
          />
        </div>

        <div style={{ display: 'flex', gap: 12 }}>
          <button
            className="btn btn-success"
            onClick={handleApprove}
            disabled={loading || !requestID}
          >
            {loading ? 'Processing...' : 'Approve'}
          </button>
          <button
            className="btn btn-danger"
            onClick={handleDeny}
            disabled={loading || !requestID}
          >
            {loading ? 'Processing...' : 'Deny'}
          </button>
        </div>

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>
    </div>
  );
}
