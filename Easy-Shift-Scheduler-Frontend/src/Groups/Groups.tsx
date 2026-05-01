import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { createGroup, joinGroup, removeUserFromGroup } from '../api/groups';
import type { Group } from '../types';
import '../components/CSS/Layout.css';
import './CSS/Groups.css';

export default function Groups() {
  const { user } = useAuth();

  const [userIDInput, setUserIDInput] = useState('');
  const [selectedUserIDs, setSelectedUserIDs] = useState<number[]>([]);
  const [createdGroup, setCreatedGroup] = useState<Group | null>(null);

  const [joinGroupID, setJoinGroupID] = useState('');
  const [joinUserID, setJoinUserID] = useState('');

  const [removeGroupID, setRemoveGroupID] = useState('');
  const [removeEmployeeID, setRemoveEmployeeID] = useState('');

  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  function addUserID() {
    const id = Number(userIDInput.trim());
    if (id && !selectedUserIDs.includes(id)) {
      setSelectedUserIDs(ids => [...ids, id]);
    }
    setUserIDInput('');
  }

  function removeUserID(id: number) {
    setSelectedUserIDs(ids => ids.filter(i => i !== id));
  }

  async function handleCreateGroup(e: React.FormEvent) {
    e.preventDefault();
    if (selectedUserIDs.length === 0) {
      setStatus('Add at least one user ID.');
      return;
    }
    setStatus('');
    setLoading(true);
    try {
      const group = await createGroup(selectedUserIDs);
      setCreatedGroup(group);
      setSelectedUserIDs([]);
      setStatus('Group created successfully!');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to create group.');
    } finally {
      setLoading(false);
    }
  }

  async function handleJoinGroup(e: React.FormEvent) {
    e.preventDefault();
    const uid = Number(joinUserID || user?.id);
    setStatus('');
    setLoading(true);
    try {
      const msg = await joinGroup(uid, Number(joinGroupID));
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to join group.');
    } finally {
      setLoading(false);
    }
  }

  async function handleRemoveUser(e: React.FormEvent) {
    e.preventDefault();
    if (!user) return;
    setStatus('');
    setLoading(true);
    try {
      const msg = await removeUserFromGroup(Number(removeGroupID), Number(removeEmployeeID), user.id);
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to remove user.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Groups</h1>

      <div className="card">
        <h2 className="card-title">Create Group</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Add employee IDs to form a group for schedule management.
        </p>
        <form onSubmit={handleCreateGroup}>
          <div className="form-group">
            <label className="form-label">Employee IDs</label>
            {selectedUserIDs.length > 0 && (
              <div className="user-id-tags">
                {selectedUserIDs.map(id => (
                  <span key={id} className="user-id-tag">
                    ID: {id}
                    <button type="button" onClick={() => removeUserID(id)}>×</button>
                  </span>
                ))}
              </div>
            )}
            <div className="add-user-row">
              <input
                className="form-input"
                type="number"
                placeholder="Enter employee ID"
                value={userIDInput}
                onChange={e => setUserIDInput(e.target.value)}
                onKeyDown={e => { if (e.key === 'Enter') { e.preventDefault(); addUserID(); } }}
                style={{ flex: 1 }}
              />
              <button type="button" className="btn btn-secondary" onClick={addUserID}>Add</button>
            </div>
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading || selectedUserIDs.length === 0}>
            {loading ? 'Creating...' : 'Create Group'}
          </button>
        </form>

        {createdGroup && (
          <div style={{ marginTop: 16, padding: 12, background: '#a8c8e8', borderRadius: 6 }}>
            <p style={{ margin: '0 0 8px', fontWeight: 600, color: '#384959' }}>Group Created — ID: {createdGroup.id}</p>
            <div className="group-member-list">
              {createdGroup.users?.map(u => (
                <div key={u.id} className="group-member">
                  <span>{u.username}</span>
                  <span className="group-member-id">ID: {u.id}</span>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      <div className="card">
        <h2 className="card-title">Add User to Group</h2>
        <form onSubmit={handleJoinGroup}>
          <div className="form-group">
            <label className="form-label" htmlFor="joinGroupID">Group ID</label>
            <input
              id="joinGroupID"
              className="form-input"
              type="number"
              placeholder="Enter group ID"
              value={joinGroupID}
              onChange={e => setJoinGroupID(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="joinUserID">User ID (leave blank to add yourself)</label>
            <input
              id="joinUserID"
              className="form-input"
              type="number"
              placeholder={`Your ID: ${user?.id}`}
              value={joinUserID}
              onChange={e => setJoinUserID(e.target.value)}
            />
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading || !joinGroupID}>
            {loading ? 'Processing...' : 'Join Group'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2 className="card-title">Remove User from Group</h2>
        <form onSubmit={handleRemoveUser}>
          <div className="form-group">
            <label className="form-label" htmlFor="removeGroupID">Group ID</label>
            <input
              id="removeGroupID"
              className="form-input"
              type="number"
              placeholder="Enter group ID"
              value={removeGroupID}
              onChange={e => setRemoveGroupID(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="removeEmployeeID">Employee ID to Remove</label>
            <input
              id="removeEmployeeID"
              className="form-input"
              type="number"
              placeholder="Enter employee ID"
              value={removeEmployeeID}
              onChange={e => setRemoveEmployeeID(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-danger" type="submit" disabled={loading || !removeGroupID || !removeEmployeeID}>
            {loading ? 'Processing...' : 'Remove User'}
          </button>
        </form>
      </div>

      {status && (
        <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') || status.toLowerCase().includes('failed') ? 'error' : 'success'}`}>
          {status}
        </div>
      )}
    </div>
  );
}
