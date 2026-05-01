import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import type { ReactNode } from 'react';

interface Props {
  children: ReactNode;
  requireEmployer?: boolean;
}

export default function ProtectedRoute({ children, requireEmployer = false }: Props) {
  const { user, isEmployer } = useAuth();

  if (!user) return <Navigate to="/login" replace />;
  if (requireEmployer && !isEmployer) return <Navigate to="/dashboard" replace />;

  return <>{children}</>;
}
